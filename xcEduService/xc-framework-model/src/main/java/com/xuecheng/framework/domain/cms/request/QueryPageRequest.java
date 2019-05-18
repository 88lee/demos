package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LiYuan
 * Created on 2018/10/31.
 */
@Data
public class QueryPageRequest extends RequestData {

    @ApiModelProperty("站点id")
    private String siteId;

    @ApiModelProperty("页面ID")
    private String pageId;

    @ApiModelProperty("页面名称")
    private String pageName;

    @ApiModelProperty("页面别名")
    private String pageAliase;

    @ApiModelProperty("模版id")
    private String templateId;

}