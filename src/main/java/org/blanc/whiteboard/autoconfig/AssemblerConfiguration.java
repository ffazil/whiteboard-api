package org.blanc.whiteboard.autoconfig;

import com.tracebucket.tron.autoconfig.NonExistingAssemblerBeans;
import com.tracebucket.tron.context.EnableAutoAssemblerResolution;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(value = NonExistingAssemblerBeans.class)
@EnableAutoAssemblerResolution(basePackages = {"org.blanc.whiteboard.rest.assembler"})
public class AssemblerConfiguration {
}