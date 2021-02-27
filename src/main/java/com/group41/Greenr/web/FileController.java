package com.group41.Greenr.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.group41.Green.web.payload.FileUploadUtil;
import com.group41.Green.web.payload.UploadFileResponse;
import com.group41.Greenr.model.User;
import com.group41.Greenr.repository.UserRepo;

@RestController
public class FileController {

	@Autowired
	private UserRepo userRepo;
	
	@PostMapping("/uploadFile")
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, HttpSession session) throws Exception {
	
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (fileName.contains("..")) {
			throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
		}
		
		User user = (User) session.getAttribute("user");
		user.setFileName(fileName);
		user.setFileType(file.getContentType());
		user.setData(file.getBytes());
		
		userRepo.save(user);
		String fileDownlaodUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/")
				.path(Long.toString(user.getId()))
				.toUriString();
		
		String uploadDir = "user-photos/" + user.getId();
		FileUploadUtil.saveFile(uploadDir, fileName, file);
		return new UploadFileResponse(user.getFileName(), fileDownlaodUri, file.getContentType(), file.getSize());
		//return "profile";
	}
	
	@GetMapping("/uploadFiles")
	public String uploadFiles(@RequestParam("file") MultipartFile file) {
		System.out.print("sss");
			return "profile";
	}
}
