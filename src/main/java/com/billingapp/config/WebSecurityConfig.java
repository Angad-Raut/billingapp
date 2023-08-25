package com.billingapp.config;

import com.billingapp.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig implements WebMvcConfigurer {

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(jwtUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests().antMatchers("/auth/login","/auth/logout","/addUser",
                        "/api/customerBilling/placeOrder","/api/customerBilling/makePayment",
                        "/api/customerBilling/getOrderDetailsByOrderId","/api/customerBilling/getAllCustomersOrdersByUserId",
                        "/api/customerBilling/getOrderAndPaymentByOrderId","/api/agentDetails/addAgent","/api/agentDetails/getAgentById",
                        "/api/agentDetails/updateAgentStatusById","/api/agentDetails/getAllAgentDetailsByUserId",
                        "/api/cropDetails/addCrop","/api/cropDetails/getCropById","/api/cropDetails/getCropDropDownByUserId",
                        "/api/cropDetails/updateCropStatusById","/api/cropDetails/getAllCropDetailsByUserId",
                        "/api/cropSetting/addCropSetting","/api/cropSetting/getCropSettingById",
                        "/api/cropSetting/updateCropSettingStatusById","/api/cropSetting/getAllCropSettingDetailsByUserId",
                        "/api/cropSetting/getCropSettingByUserId","/api/customerDetails/addCustomer","/api/customerDetails/getCustomerById",
                        "/api/customerDetails/updateCustomerStatusById","/api/customerDetails/getAllCustomerDetailsByUserId",
                        "/api/customerDetails/getCustomerDetailsByMobile",
                        "/api/discounts/addDiscount","/api/discounts/getDiscountById",
                        "/api/discounts/getDiscountTypes","/api/discounts/getAllDiscountsByUserId",
                        "/api/discounts/getDiscountDropDownByUserId").permitAll().
                       anyRequest().authenticated().and().
                       exceptionHandling()
                .authenticationEntryPoint((req, res, ex) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED : " + ex.getMessage()))
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(authenticationProvider());
        http.logout()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutUrl("/auth/logout")
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .logoutSuccessUrl("/*");
        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

}
