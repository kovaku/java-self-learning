package org.github.kovaku;

import org.github.kovaku.config.TestConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(classes = TestConfig.class)
public class BaseTest extends AbstractTestNGSpringContextTests {

}
