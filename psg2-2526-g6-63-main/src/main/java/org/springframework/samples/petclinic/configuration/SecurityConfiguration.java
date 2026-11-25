package org.springframework.samples.petclinic.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.samples.petclinic.configuration.jwt.AuthEntryPointJwt;
import org.springframework.samples.petclinic.configuration.jwt.AuthTokenFilter;
import org.springframework.samples.petclinic.configuration.services.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Autowired
	DataSource dataSource;

	private static final String ADMIN = "ADMIN";
	private static final String CLINIC_OWNER = "CLINIC_OWNER";
	private static final String VET = "VET";
	private static final String OWNER = "OWNER";

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

		http
			.cors(withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.disable()))
			.exceptionHandling((exepciontHandling) -> exepciontHandling.authenticationEntryPoint(unauthorizedHandler))

			.authorizeHttpRequests(authorizeRequests -> authorizeRequests
				.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.OPTIONS, "/**")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/resources/**")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/webjars/**")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/static/**")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-resources/**")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/clinics")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/oups")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/auth/**")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/v3/api-docs/**")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui.html")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui/**")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/developers")).permitAll()

				.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/plan")).hasAuthority(OWNER)
				.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/users/vets")).hasAnyAuthority(CLINIC_OWNER)
				.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/users/**")).hasAuthority(ADMIN)

				.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/clinicOwners/all")).hasAuthority(ADMIN)
				.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/clinicOwners/**")).hasAnyAuthority(ADMIN, CLINIC_OWNER)

				// ✅ IMPORTANT: allow DELETE of tickets (messages) under consultations for authenticated users
				// Put this BEFORE the general consultations/** DELETE matcher
				.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/api/v1/consultations/*/tickets/**")).authenticated()

				// ✅ Allow DELETE of consultations for authenticated users (final authorization is enforced in controller)
				.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/api/v1/consultations/**")).authenticated()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/adoptions/**")).hasAuthority(OWNER)
				.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/owners/current")).hasAuthority(OWNER)
				.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/owners/**")).hasAuthority(ADMIN)
				.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/visits/**")).authenticated()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/pets/stats")).hasAuthority(ADMIN)
				.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/pets")).authenticated()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/pets/**")).authenticated()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/photos/**")).authenticated()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/community/**")).authenticated()

				.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/clinics/**")).hasAnyAuthority(CLINIC_OWNER, ADMIN)

				.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/vets/stats")).hasAuthority(ADMIN)
				.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/vets/**")).authenticated()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/vets/**")).hasAnyAuthority(ADMIN, VET, CLINIC_OWNER)

				.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()

				// Necesario para React Router (SPA)
				.requestMatchers(RegexRequestMatcher.regexMatcher("^(?!api|public|css|js|images|uploads|swagger|v3|h2-console)[^\\.]*")).permitAll()
				.requestMatchers(RegexRequestMatcher.regexMatcher("(.*\\..+)$")).permitAll()

				.anyRequest().authenticated()
			)

			.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}