package com.thinkersol.API.repositories.Student;

import java.util.List;

public interface CareerSearchRepository {
    List<String> getCareersOnSearch(String careerQuery);
}
