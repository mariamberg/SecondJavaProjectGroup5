import org.example.CreateTranscript;
import org.example.DownloadSyllabus;
import org.example.DownloadTranscript;
import org.example.ExaminationDate;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class testCases {

    @Test
    void checkFinalExaminationDate() {
        ExaminationDate.checkFinalExaminationDate();
        // Check if the date is correct
        String expectedFinalDate = "2023-04-17";
        assertEquals(expectedFinalDate, ExaminationDate.examinationDate);
    }

    @Test
    void checkIfTranscriptCanBeCreated(){
        CreateTranscript.CreateTranscript();
        // Check if new transcript exists
        assertTrue(CreateTranscript.getNewTranscript());
    }

    @Test
    void checkIfTranscriptIsDownloaded(){
        DownloadTranscript.downloadTranscript();
        // Check if the file exists
        assertTrue(new File("target/downloads/Intyg.pdf").exists());
    }

    @Test
    void checkIfSyllabusCanBeDownloaded() {
        DownloadSyllabus.downloadSyllabus();
        // Check if the file exists
        assertTrue(new File("target/downloads/kursplan.pdf").exists());
    }

}