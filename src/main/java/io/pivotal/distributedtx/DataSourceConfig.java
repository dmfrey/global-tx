package io.pivotal.distributedtx;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

	@Bean
	@Primary
	@ConfigurationProperties( prefix = "datasource.ds1" )
	public DataSource dataSourceOne() {
		
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		builder.setType( EmbeddedDatabaseType.H2 )
			.generateUniqueName( true )
			.addScript( "classpath:ds1-schema.sql" );
		
		return builder.build();
	}
	
	@Bean
	@Primary
	public JdbcTemplate jdbcTemplateOne( @Qualifier( "dataSourceOne" ) final DataSource dataSourceOne ) {
		
		return new JdbcTemplate( dataSourceOne );
	}
	
	@Bean
	@ConfigurationProperties( prefix = "datasource.ds2" )
	public DataSource dataSourceTwo() {
		
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		builder.setType( EmbeddedDatabaseType.H2 )
			.generateUniqueName( true )
			.addScript( "classpath:ds2-schema.sql" );
		
		return builder.build();
	}

	@Bean
	public JdbcTemplate jdbcTemplateTwo( @Qualifier( "dataSourceTwo" ) final DataSource dataSourceTwo ) {
		
		return new JdbcTemplate( dataSourceTwo );
	}

}
