package com.springboot.cloud.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayParamFlowItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @PackageName:com.springboot.cloud.gateway.config
 * @ClassName:GatewayConfiguration
 * @Description
 * @Author 志雄 Administrator
 * @Date 2020/11/13 10:44
 **/
@Configuration
public class GatewayConfiguration {
    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayConfiguration(ObjectProvider<List<ViewResolver>>
                                        viewResolversProvider,
                                ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers =
                viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    /**
     * 配置限流的异常处理器:SentinelGatewayBlockExceptionHandler
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler
    sentinelGatewayBlockExceptionHandler() {
        return new SentinelGatewayBlockExceptionHandler(viewResolvers,
                serverCodecConfigurer);
    }

    /**
     * 配置限流过滤器
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    /**
     * 配置初始化的限流参数
     */
    @PostConstruct
    public void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        rules.add(
                //资源名称
                new GatewayFlowRule("order-service")
                        // 限流阈值
                        .setCount(1)
                        // 统计时间窗口，单位是秒，默认是 1 秒
                        .setIntervalSec(1)
        );
        // 参数限流
        //通过指定PARAM_PARSE_STRATEGY_URL_PARAM表示从url中获取参数，setFieldName指定参数名称
        rules.add(new GatewayFlowRule("order-service")
                .setCount(1)
                .setIntervalSec(1)
                .setParamItem(new GatewayParamFlowItem()
                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_URL_PARAM).setFieldName("id")
                )
        );
        GatewayRuleManager.loadRules(rules);
    }

    /**
     * 自定义异常提示
     */
    @PostConstruct
    public void initBlockHandlers() {
        BlockRequestHandler blockRequestHandler = (serverWebExchange, throwable) -> {
            Map map = new HashMap<>();
            map.put("code", 001);
            map.put("message", "对不起,接口限流了");
            return ServerResponse.status(HttpStatus.OK).
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    body(BodyInserters.fromObject(map));
        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }

    /**
     *自定义API分组
     */
    @PostConstruct
    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();
        ApiDefinition api1 = new ApiDefinition("product_api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    //以/product-service/product 开头的请求
                    add(new ApiPathPredicateItem().setPattern("/productservice/product/**").
                            setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                }});
        ApiDefinition api2 = new ApiDefinition("order_api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    ///order-service/order 完成的url路径匹配
                    add(new ApiPathPredicateItem().setPattern("/order-service/order"));
                }});
        definitions.add(api1);
        definitions.add(api2);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }
}
