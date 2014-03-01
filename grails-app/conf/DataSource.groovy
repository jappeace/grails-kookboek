dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"

	username = "kook"
	password = "waerioj2390fs!!jfw3dssWSA"

}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' 
}

// environment specific settings
environments {

	host = "10.0.0.98"
    development {
        dataSource {
			driverClassName = "org.h2.Driver"
			username = "sa"
			password = ""
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }
    }
    test {
        dataSource {
            dbCreate = "create-drop"
            url = "jdbc:mysql://${host}/kook_test?useUnicode=yes&characterEncoding=UTF-8"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://${host}/kook_prod?useUnicode=yes&characterEncoding=UTF-8"
            properties {
               maxActive = -1
               minEvictableIdleTimeMillis=1800000
               timeBetweenEvictionRunsMillis=1800000
               numTestsPerEvictionRun=3
               testOnBorrow=true
               testWhileIdle=true
               testOnReturn=false
               validationQuery="SELECT 1"
               jdbcInterceptors="ConnectionState"
            }
        }
    }
}
