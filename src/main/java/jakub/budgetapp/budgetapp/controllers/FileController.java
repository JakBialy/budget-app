package jakub.budgetapp.budgetapp.controllers;

import jakub.budgetapp.budgetapp.dtos.FinancialOperation;
import jakub.budgetapp.budgetapp.services.implementations.MainCsvReaderServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files-upload")
public class FileController {

    private final MainCsvReaderServiceImpl readerService;

    public FileController(MainCsvReaderServiceImpl readerService) {
        this.readerService = readerService;
    }

    @PostMapping("/csv")
    public List<List<FinancialOperation>> csvController (@RequestParam MultipartFile file){
        return readerService.mainCSVReading(file);
    }

}
