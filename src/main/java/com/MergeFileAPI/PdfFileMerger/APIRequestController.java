package com.MergeFileAPI.PdfFileMerger;

import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.*;
import java.util.UUID;

@RestController
public class APIRequestController {
    private String basePath = "C:\\Users\\Yash\\Desktop\\";

    @RequestMapping(
            value = "/merger",
            method = RequestMethod.POST
    )
    public @ResponseBody byte[] Merger(@RequestBody MultipartFile[] files) throws FileNotFoundException {
        PDFMergerUtility pmu = new PDFMergerUtility();
        UUID uid = UUID.randomUUID();
        File mergedFile;
        try {
            for(MultipartFile file : files)
                pmu.addSource(file.getInputStream());
            pmu.setDestinationFileName(basePath + uid.toString() + ".pdf");
            pmu.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
        } catch(IOException ioe) {
            //handle io exception
        }
        mergedFile = new File(basePath + uid.toString() + ".pdf");

        try {
            return IOUtils.toByteArray(new FileInputStream(mergedFile));
        } catch (IOException e) {
            //io exception
            return "FAIL".getBytes();
        }


        /*Resource file = storageService.loadAsResource(basePath + uid.toString() + ".pdf");
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + "mergedFile.pdf" + "\"").body(file); */
    }
}