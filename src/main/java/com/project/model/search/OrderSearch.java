package com.project.model.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class OrderSearch {

    private String s_firstname;
    private String s_lastname;
    private String s_phone;

    private String s_unit;
    private String s_status;

    public void normalize(){
        if(StringUtils.isEmpty(this.s_lastname)){
            this.s_lastname = null;
        }
        if(StringUtils.isEmpty(this.s_firstname)){
            this.s_firstname = null;
        }
        if(StringUtils.isEmpty(this.s_phone)){
            this.s_phone = null;
        }


        if(StringUtils.isEmpty(this.s_unit)){
            this.s_unit = null;
        }
        if(StringUtils.isEmpty(this.s_status)){
            this.s_status = null;
        }
    }
}
