package com.soundaddiction.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.soundaddiction.util.Checker;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FilesController {

    private static final String IO_ERROR_MESSAGE = "An error occured while uploading your file. " +
                                                                                "Please try again!";
    private static final String IMAGES_FILEPATH = "C:\\song_images";
    private static final String SONGS_FILEPATH = "C:\\song_mp3s";
    private static final String NO_IMAGE = "no-photo.jpg";

    public static final String uploadSong(MultipartFile uploadedFile, String filename)
                                                                    throws IOException{
        if(FilenameUtils.getExtension(uploadedFile.getOriginalFilename()).equalsIgnoreCase("mp3")) {
            return uploadFile(uploadedFile, SONGS_FILEPATH, filename);
        }
        return null;
    }

    public static final String uploadImage(MultipartFile uploadedFile, String filename)
                                                                    throws IOException {
        if(FilenameUtils.getExtension(uploadedFile.getOriginalFilename()).equalsIgnoreCase("jpg")) {
            return uploadFile(uploadedFile, IMAGES_FILEPATH, filename);
        }
        return null;
    }

    private static final String uploadFile(MultipartFile uploadedFile, String filepath, String filename)
                                                                                    throws IOException {
        try {
            //Check if file is empty
            if(uploadedFile.isEmpty()) {
                return null;
            }

            //If the given filename is null
            if(filename == null || filename.isEmpty()) {
                filename = uploadedFile.getOriginalFilename();
            }

            //Create new file at the given destination
            File newFile = new File(filepath + File.separator + filename);

            //Copy multipart file data into the new file
            Files.copy(uploadedFile.getInputStream(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return newFile.getName();
        }
        catch (IOException e) {
            throw new IOException(IO_ERROR_MESSAGE, e);
        }
    }

    @RequestMapping(value = "/getImg", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getPicture(@RequestParam("img") String pic) throws IOException {
        //Grab the file where the image is saved at
        File f = new File(IMAGES_FILEPATH + File.separator + pic);

        //Check if the file exists
        if(!f.exists() || !Checker.isNotNullOrEmpty(pic)) {
            //Assign the no image photo to the song without an image
            f = new File(IMAGES_FILEPATH + File.separator + NO_IMAGE);
        }

        byte[] byteArray = new byte[(int) f.length()];
        //Write the image in a byte array and return it
        try(BufferedInputStream is = new BufferedInputStream(new FileInputStream(f));){
            is.read(byteArray);
        }

        return byteArray;
    }

    @RequestMapping(value = "/getSong", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getTrailer(@RequestParam("song") String trailer) throws IOException {

        //Grab the file where the song is saved at
        File f = new File(SONGS_FILEPATH + File.separator + trailer);

        byte[] byteArray = new byte[(int) f.length()];
        //Write the song in a byte array and return it
        try(BufferedInputStream is = new BufferedInputStream(new FileInputStream(f));){
            is.read(byteArray);
        }
        return byteArray;
    }
}
