package com.kchandrakant.learning.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

public class DroolsBeanFactory {

	private static KieServices kieServices = KieServices.Factory.get();
	
	public static KieSession getKieSession() throws IOException {
	    return getKieContainer().newKieSession();
	}

	private static KieContainer getKieContainer() throws IOException {
		KieRepository kieRepository = kieServices.getRepository();

		kieRepository.addKieModule(new KieModule() {
			public ReleaseId getReleaseId() {
				return kieRepository.getDefaultReleaseId();
			}
		});

		kieServices.newKieBuilder(getKieFileSystem()).buildAll();

		return kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
	}

	private static KieFileSystem getKieFileSystem() throws IOException {
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		List<String> rules = Arrays.asList("demo.drl");
		for (String rule : rules) {
			kieFileSystem.write(ResourceFactory.newClassPathResource(rule));
		}
		return kieFileSystem;

	}

}
