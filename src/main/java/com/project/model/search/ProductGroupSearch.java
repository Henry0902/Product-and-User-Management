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
public class ProductGroupSearch {
    private String s_pname;
    private Integer s_status;

    public void normalize(){
        if(StringUtils.isEmpty(this.s_pname)){
            this.s_pname = null;
        }
        if(StringUtils.isEmpty(this.s_status)){
            this.s_status = null;
        }
    }
}
