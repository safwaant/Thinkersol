package com.thinkersol.API.repositories.Student;

import com.thinkersol.API.repositories.GlobalReturnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class jdbcCareerSearchRepository implements CareerSearchRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    GlobalReturnsRepository globalReturnsRepository;

    @Override
    public List<String> getCareersOnSearch(String careerQuery) {

        if(careerQuery == null){
            return null;
        }

        String sql = """
                SELECT soc_code, occ_code, career_name\s
                FROM Career\s
                WHERE Career.career_name ILIKE '?%'
                """;
        List<String> result = null;
        try{
            result= jdbcTemplate.queryForList(sql, String.class, careerQuery);
        }catch(Exception e){

        }
        return result;
    }
}
