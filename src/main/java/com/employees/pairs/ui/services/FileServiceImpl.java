package com.employees.pairs.ui.services;

import com.employees.pairs.model.EmployeeData;
import com.employees.pairs.model.PairsResponse;
import com.employees.pairs.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Service("UiFileService")
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileUtils fileUtils;

    @Override
    public void populateModelWithPairs(RedirectAttributes model, MultipartFile file, char delimiter) {
        List<EmployeeData> employeesData = fileUtils.getEmployeesData(file, delimiter);

        model.addFlashAttribute("list", employeesData);

        List<PairsResponse> pairedEmployees = fileUtils.getSortedPairedEmployees(employeesData);

        model.addFlashAttribute("datagrid", pairedEmployees);

        PairsResponse longestOverlap = pairedEmployees.getFirst();

        model.addFlashAttribute("longestOverlap", longestOverlap);
    }
}
