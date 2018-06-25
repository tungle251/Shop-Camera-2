package com.spring.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jayway.jsonpath.internal.Path;

@Controller
public class TestController {
	private static String PATH = "C:\\Users\\TungLe\\Desktop\\New folder\\";

	@RequestMapping("/CameraGiamSat/uploadForm")
	public String testTemplate() {
		return "test/upload";
	}

	@RequestMapping(value = "/CameraGiamSat/doUpload", method = RequestMethod.POST)
	public String upload(@RequestParam MultipartFile file) throws IOException {

		if (!file.isEmpty()) {

			String fileName = file.getOriginalFilename();
			InputStream is = file.getInputStream();

			Files.copy(is, Paths.get(PATH + fileName), StandardCopyOption.REPLACE_EXISTING);

			return "redirect:/success.html";

		} else {

			return "redirect:/failure.html";
		}

	}
}
