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
public class UserSearch {
    private String s_uname;
    private String s_fname;
    private String s_email;
    private String s_unit;
    private Integer s_status;

    public void normalize(){
        if(StringUtils.isEmpty(this.s_uname)){
            this.s_uname = null;
        }
        if(StringUtils.isEmpty(this.s_fname)){
            this.s_fname = null;
        }
        if(StringUtils.isEmpty(this.s_email)){
            this.s_email = null;
        }
        if(StringUtils.isEmpty(this.s_unit)){
            this.s_unit = null;
        }
        if(StringUtils.isEmpty(this.s_status)){
            this.s_status = null;
        }
    }
}
