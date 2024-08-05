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
public class UserGroupSearch {
    private String s_gname;
    private String s_desc;
    private Integer s_status;

    public void normalize(){
        if(StringUtils.isEmpty(this.s_gname)){
            this.s_gname = null;
        }
        if(StringUtils.isEmpty(this.s_desc)){
            this.s_desc = null;
        }
        if(StringUtils.isEmpty(this.s_status)){
            this.s_status = null;
        }
    }
}
