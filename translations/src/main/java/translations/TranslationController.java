package translations;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import translations.domain.BadJsonException;
import translations.domain.Query;
import translations.domain.QueryType;
import translations.domain.Translation;

@Controller
public class TranslationController {


//	@Value("${translation.dir}")
//	private String translationDirName;
	
	private Map<String, Translation> translations;
	
	@PostConstruct
	public void init() {
		translations = new HashMap<String, Translation>();
		
		File translationDir = null;
		try {
		//	translationDir = new ClassPathResource(translationDirName).getFile();
			translationDir = new ClassPathResource("translation-files").getFile();
			System.out.println("Working Directory = " +
		              System.getProperty("user.dir"));
		//	translationDir = translationDirName.getFile();
			
			for (File file : getTranslationFiles(translationDir)) {
				translations.put(getName(file), build(file));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private File[] getTranslationFiles(File dir) {
		return dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name){
				return name.endsWith(".json");
			}
		});
	}

	private Translation build(File file) {
		ObjectMapper jsonMapper = new ObjectMapper();
		Translation t = null;
		try {
			Map<String, Object> map = jsonMapper.readValue(file, HashMap.class);
			t = new Translation(map);
		} catch (IOException e) {
			// TODO Inform user of error
			e.printStackTrace();
		} catch (BadJsonException e) {
			// TODO Inform user of error
			e.printStackTrace();

		}
		return t;
	}

	private String getName(File file) {
		return file.getName().substring(0, file.getName().lastIndexOf(".json"));
	}

	@GetMapping("/")
	public String lookup(Model model) {
		model.addAttribute("lookupTypes", QueryType.values());
		model.addAttribute("translationFiles", translations.keySet());
		return "lookup";
	}

	@PostMapping("/")
	public String lookupSubmit(@ModelAttribute("query") Query query, Model model) {
		List<String> results = null;
		Translation t = translations.get(query.getTranslationFile());
		
		if (query.isRegularLookup()) {
			String message = t.getMessage(query.getQueryString());
			if (message != null) {
				results = new ArrayList<String>();
				results.add(message);
			}
		} else {
			results = t.getLabels(query.getQueryString());
		}

		if (results != null && !results.isEmpty()) {
			model.addAttribute("results", results);
		} else {
			model.addAttribute("noResults", true);
		}

		model.addAttribute("lookupTypes", QueryType.values());
		model.addAttribute("translationFiles", translations.keySet());
		return "lookup";
	}

	@ModelAttribute("query")
	public Query getQueryObject() {
		return new Query();
	}
}