//
// Built on Sat Nov 12 07:33:52 UTC 2016 by logback-translator
// For more information on configuration files in Groovy
// please see http://logback.qos.ch/manual/groovy.html

// For assistance related to this tool or configuration files
// in general, please contact the logback user mailing list at
//    http://qos.ch/mailman/listinfo/logback-user

// For professional support please see
//   http://www.qos.ch/shop/products/professionalSupport

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.ThresholdFilter
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import java.nio.charset.Charset

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.INFO
import static ch.qos.logback.classic.Level.WARN

println("Load logback configuration.")

scan("300 seconds")
def LOG_HOME = System.getenv("OUTPUT_HOME")
if (!LOG_HOME) {
  LOG_HOME = "/data/var/log/mljr"
}

def appName = System.getenv("APP_NAME")
if (!appName) {
  appName = "mljr"
}
context.name = appName

appender("STDOUT", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = DEBUG
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${LOG_HOME}/spider-debug-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
    // charset = Charset.forName("UTF-8")
  }
}
appender("A1", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${LOG_HOME}/spider-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
    // charset = Charset.forName("UTF-8")
  }
}
appender("GPS-DATA", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${LOG_HOME}/GPS-%d{yyyy-MM-dd-HH}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
    // charset = Charset.forName("UTF-8")
  }
}
appender("JUHE-MOBILE-DATA", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${LOG_HOME}/JUHE-MOBILE-%d{yyyy-MM-dd-HH}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
    // charset = Charset.forName("UTF-8")
  }
}
logger("com.alibaba.druid.filter.stat.StatFilter", INFO)
logger("com.alibaba.druid.pool.DruidDataSource", INFO)
logger("com.alibaba.druid", INFO)
logger("druid.sql", WARN)
logger("druid.sql.DataSource", WARN)
logger("druid.sql.Connection", WARN)
logger("druid.sql.Statement", WARN)
logger("org.springframework.security", WARN)
logger("org.springframework", WARN)
logger("com.ibatis", INFO)
logger("java.sql", INFO)
logger("org.apache.ibatis", INFO)
logger("org.mybatis.spring", INFO)
logger("java.sql.ResultSet", INFO)
logger("java.sql.Connection", INFO)
logger("java.sql.Statement", INFO)
logger("java.sql.PreparedStatement", INFO)
logger("com.ucloud.umq", INFO)
logger("org.apache.http", INFO)
logger("org.I0Itec.zkclient", INFO)
logger("us.codecraft.webmagic", INFO)
logger("io.grpc", INFO)
logger("com.mljr.spider.scheduler.manager.Manager", DEBUG)
logger("com.mljr.spider.scheduler.manager", DEBUG)
logger("com.mljr.spider.scheduler", DEBUG)
logger("com.mljr.spider", DEBUG)
logger("gps-data", INFO, ["GPS-DATA"], false)
logger("juhe-mobile-data", INFO, ["JUHE-MOBILE-DATA"], false)
root(DEBUG, ["A1", "STDOUT"])