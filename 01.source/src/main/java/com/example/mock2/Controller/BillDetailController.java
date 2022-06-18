package com.example.mock2.Controller;

import com.example.mock2.Service.BillDetailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class BillDetailController {

    private BillDetailService billDetailService;
}
