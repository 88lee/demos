package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LiYuan
 * Created on 2018/10/31.
 */
@Data
public class QuerySiteRequest extends RequestData {

    @ApiModelProperty("站点id")
    private String siteId;

    @ApiModelProperty("站点名称")
    private String siteName;

}