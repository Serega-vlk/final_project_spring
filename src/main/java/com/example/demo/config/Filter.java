package com.example.demo.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.servlet.annotation.WebFilter;

@WebFilter("/")
@Component
public class Filter extends HiddenHttpMethodFilter {
}
