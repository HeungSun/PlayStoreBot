package io.userhabit.kongsuny.job.playstore;

import io.userhabit.kongsuny.common.Config;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static io.userhabit.kongsuny.common.Config.PARENT_DIR;
import static org.apache.poi.util.IOUtils.toByteArray;

/**
 * Created by kongsuny on 2017. 5. 8..
 * web으로 표시할경우?
 */

@RestController
@RequestMapping("/s")
public class JobInvokerController {
    @RequestMapping("/download")
    public @ResponseBody
    ResponseEntity getFile() throws IOException {

        ResponseEntity respEntity = null;

        byte[] reportBytes = null;

        File result = findLasylyFile();
        String fileName = result.getName();
        if (result.exists()) {
            InputStream inputStream = new FileInputStream(Config.PARENT_DIR+ "/" + fileName);
            String type = result.toURL().openConnection().guessContentTypeFromName(fileName);

            byte[] out = toByteArray(inputStream);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("content-disposition", "attachment; filename=" + fileName);
            responseHeaders.add("Content-Type", type);

            respEntity = new ResponseEntity(out, responseHeaders, HttpStatus.OK);
        } else {
            respEntity = new ResponseEntity("File Not Found", HttpStatus.OK);
        }
        return respEntity;
    }

    private File findLasylyFile() {
        File result = new File(PARENT_DIR);
        String[] list = result.list();

        long finalDate = 0;
        for (int i = 0; i < list.length; i++) {
            long date = Long.valueOf(list[i].replace(Config.EXCEL_FILE_NAME, ""));
            if (finalDate < date) {
                finalDate = date;
            }
        }

        return new File(Config.PARENT_DIR, finalDate + Config.EXCEL_FILE_NAME);

    }

}
