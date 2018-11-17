package com.quality.common.config;



import com.quality.common.handler.LoginOutHandler;
import com.quality.common.handler.MyAccessDeniedHandler;
import com.quality.common.handler.SuccessHandler;
import com.quality.system.service.IQualityUserService;
import com.quality.system.service.impl.QualityUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {//1

    private String forwardUrl = "/staitc/home.html";

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
	private QualityUserServiceImpl userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				//.and()
				//.sessionManagement()
					//session失效后的处理路径，
					//.invalidSessionUrl("/session/invalid")
					//session存在的最大数量，后者会替换前者
					//.maximumSessions(1)
                    //当session登录超过最大数量，禁止后续的登录行为
                   // .maxSessionsPreventsLogin(true)
                    //记录后者替换前者登录的行为，需要一个实现类，这里没有写
					/*.expiredSessionStrategy("")*/
				//.and()
                .logout()
                  //覆盖默认的logout链接，让制定的链接登录退出
                  /*  .logoutUrl("")*/
                      //退出成功后返回的url
                   //.logoutSuccessUrl("")
                     //退出之后记录个日志什么的处理逻辑
                   // .logoutSuccessHandler()
                //退出的时候，删除cookies，制定cookies的names
                    //.deleteCookies("JSESSIONID")
				.and()
				.authorizeRequests()
                    .antMatchers("/images/captcha",
                            "/login.html","/assets/**","/module/**","/index.html","/swagger-resources/**").permitAll()
                     //声明方法必须是post方法
                    .antMatchers(HttpMethod.POST,"/loginToken").permitAll()
                    .and().httpBasic().disable()
                    .csrf().disable()
                    .formLogin()
                    //定义登录页面
                    .loginPage("/login.html")
                    //定义登录的处理请求

                    .loginProcessingUrl("/loginToken")
                    .successHandler(loginSuccessHandel())
				.and()
				.authorizeRequests().anyRequest().authenticated();



	}



	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService) //设置访问数据库的请求
            .passwordEncoder(passwordEncoder()); //设计密码对比方式
	}


	/**
	 * springboot2.0 必须声明AuthenticationManager
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

    /**
     * 解决静态资源被拦截的问题
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui",
                "/swagger-resources", "/configuration/security",
                "/swagger-ui.html", "/webjars/**","/static/**/**/");

    }





    @Bean
	@Order(1)
	public SuccessHandler loginSuccessHandel(){
		return new SuccessHandler(forwardUrl);
	}

	@Bean
	public MyAccessDeniedHandler loginFaileHandel(){
		return new MyAccessDeniedHandler();
	}

	@Bean
	public LoginOutHandler loginOutHandler(){
		return new LoginOutHandler();
	}
}
