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
public class ProductSearch {
    private String s_pname;
    private String s_porigin;
    private Integer s_status;

    public void normalize(){
        if(StringUtils.isEmpty(this.s_pname)){
            this.s_pname = null;
        }
        if(StringUtils.isEmpty(this.s_porigin)){
            this.s_porigin = null;
        }
        if(StringUtils.isEmpty(this.s_status)){
            this.s_status = null;
        }
    }
}
