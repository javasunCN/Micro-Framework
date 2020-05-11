# Spring IOC

## Spring IOC - XML
```textmate
Spring使用XML注入逻辑:
    1.ClassPathXmlApplicationContext:加载Spring Config配置文件
        ClassPathXmlApplicationContext(String[] configLocations, boolean refresh, @Nullable ApplicationContext parent)
        1. ClassPathXmlApplicationContext.refresh(): 创建刷新容器
            1. prepareRefresh(): 刷新前预处理
                1. initPropertySources():初始化一些属性设置，子类自定义个性化的属性方法
                2. getEnvironment().validateRequiredProperties(): 校验属性的合法性
                3. earlyApplicationListeners = new LinkedHashSet(this.applicationListeners): 初始化监听器列表
                4. earlyApplicationEvents = new LinkedHashSet(): 初始化事件处理器列表
            2. obtainFreshBeanFactory(): 告诉子类刷新Bean工厂
                1. refreshBeanFactory(): 刷新Bean工厂，关闭前一个Bean工厂，新建一个新的Bean工厂
                    1. 如果存在Bean工厂 hasBeanFactory(): destroyBeans()、closeBeanFactory();
                    	1. destroyBeans(): 销毁Bean的方法模板
                    	2. closeBeanFactory(): 将BeanFactory的序列化ID设置为null
                    2. createBeanFactory(): 创建BeanFactory
                    	new DefaultListableBeanFactory(getInternalParentBeanFactory());
                    	1. loadBeanDefinitions(beanFactory): 通过XmlBeanDefinitionReader加载bean.xml的Bean定义	
                2. getBeanFactory(): 获取Bean工厂 ConfigurableListableBeanFactory
            3. prepareBeanFactory(beanFactory);BeanFactory的准备工作(BeanFactory进行一些设置)
            	1. 设置BeanFactory类加载器、支持SpEL表达式、添加属性编辑器
            	2. 向BeanFactory添加 BeanPostProcess(Bean后置处理器) [ApplicationContextAwareProcessor]
            	3. 指定BeanFactory要忽略自动装配接口: EnvironmentAware、EmbeddedValueResolverAware、ResourceLoaderAware、ApplicationEventPublisherAware、MessageSourceAware、ApplicationContextAware
            	4. 注册自动装配的组件: BeanFactory、ResourceLoader、ApplicationEventPublisher、ApplicationContext
            	5. 添加BeanProcessor [ApplicationListenerDetector]
            	6. 根据条件给BeanFactory注册一些可用组件: LoadTimeWeaver、Environment、System#getProperties()、System#getenv()
            4. postProcessBeanFactory(beanFactory): BeanFactory准备工作之后设置后置处理
            
 ======================== 以上是BeanFactory创建及准备工作 ========================
 
            5. invokeBeanFactoryPostProcessors(beanFactory): 调用BeanFactoryPostProcessor
            	BeanFactoryPostProcessor为BeanFactory的后置处理器，在BeanFactory标准初始化之后执行
            	BeanDefinitionRegistryPostProcessor、BeanFactoryPostProcessor
            	1. 优先处理BeanDefinitionRegistryPostProcessor
            		1. 获取所有的 BeanDefinitionRegistryPostProcessor
              	beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
              	2. 首先调用实现 PriorityOrdered 接口的 BeanDefinitionRegistryPostProcessors
              		postProcessor.postProcessBeanDefinitionRegistry(registry);
              	3. 其次调用实现 Ordered 接口的 BeanDefinitionRegistryPostProcessor
              		postProcessor.postProcessBeanDefinitionRegistry(registry);
              	4. 最后调用没有实现任何优先级或者排序接口的BeanDefinitionRegistryPostProcessors
              		postProcessor.postProcessBeanDefinitionRegistry(registry);
              2.  再处理BeanFactoryPostProcessor
              	1. 获取BeanFactoryPostProcessor类型的所有BeanFactory后置处理器
              	beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false);
              	2. 首先调用实现 PriorityOrdered 接口的 BeanFactoryPostProcessors
              	postProcessor.postProcessBeanFactory(beanFactory);
              	3. 其次调用实现 Ordered 接口的 BeanFactoryPostProcessors
              	postProcessor.postProcessBeanFactory(beanFactory);
              	4. 最后调用没有实现任何优先级或排序接口的BeanFactoryPostProcessors
              	postProcessor.postProcessBeanFactory(beanFactory);
              	
 ========== 以上是BeanFactory调用BeanFactoryPostProcessor ===============
            6. registerBeanPostProcessors(beanFactory): 注册BeanPostProcessors(BeanFactory后置处理器)
            PostProcessorRegistrationDelegate.registerBeanPostProcessors(beanFactory, this);
            
            不同接口类型的 BeanPostProcessor；在Bean创建前后的执行时机是不一样的
            BeanPostProcessor、
            DestructionAwareBeanPostProcessor、
            InstantiationAwareBeanPostProcessor、
            SmartInstantiationAwareBeanPostProcessor、
            MergedBeanDefinitionPostProcessor
            
            	1. 获取所有的BeanPostProcessor。默认可以通过PriorityOrdered、Ordered接口来执行优先级
            	2. 首先，注册实现了 PriorityOrdered 接口的 BeanPostProcessors到BeanFactory
            	beanFactory.addBeanPostProcessor(postProcessor);
            	3. 下一步，注册实现 Ordered 接口的 BeanPostProcessors 到 BeanFactory
            	beanFactory.addBeanPostProcessor(postProcessor);
            	4. 接着注册所有常规的未实现 PriorityOrdered 、Ordered 接口的 BeanPostProcessors
            	beanFactory.addBeanPostProcessor(postProcessor);
            	5. 最后，重新注册所有的 BeanPostProcessors
            	beanFactory.addBeanPostProcessor(postProcessor);
          		6. 重新注册内部Bean的ApplicationListener应用监听器探测器
  ========== 以上是BeanFactory注册BeanFactoryPostProcessor ===============        		

          	7. initMessageSource(); 初始化MessageSource组件[做国际化功能；消息绑定，消息解析]
          		1. 获取BeanFactory； getBeanFactory()
          		2. BeanFactory 存在 messageSource组件，设置父级MessageSource组件处理器HierarchicalMessageSource
          			BeanFactory 不存在 messageSource组件，设置父级MessageSource组件处理器DelegatingMessageSource
          		3. 注册 messageSource组件Bean到BeanFactory
          	
          	8. initApplicationEventMulticaster(); 初始化应用事件多播器(派发器)
          		1. 获取BeanFactory； getBeanFactory()
          		2. BeanFactory 存在 applicationEventMulticaster的Bean，设置应用多播器为ApplicationEventMulticaster
          		3. BeanFactory 不存在 applicationEventMulticaster的Bean，设置应用多播器为SimpleApplicationEventMulticaster，并注册到BeanFactory中.
          		
           9. onRefresh(); 子容器中初始化其他定义类，子类重写这个方法，在容器刷新的时候可以自定义逻辑；
           
           10. registerListeners(); 注册实现ApplicationListener的监听器
           	1. 获取所有的 ApplicationListener的监听器并将所有的监听器添加到 ApplicationEventMulticaster 
           	2. 获取所有的 ApplicationListener 并将Bean添加到 ApplicationEventMulticaster
           	3. 派发之前的应用程序事件到多播器
           
           11. finishBeanFactoryInitialization(beanFactory); 初始化所有剩余的(非延迟初始化)单实例Bean
           	1. BeanFactory存在conversionService的Bean && 存在 ConversionService类型，设置 Bean转换服务
           	2. 如果没有Bean的后置处理器，则注册默认的嵌入式值解析器之前注册的所有东西，此时主要用于解析注释属性值
           	beanFactory.addEmbeddedValueResolver(strVal -> getEnvironment().resolvePlaceholders(strVal));
            3. 注册 LoadTimeWeaverAware 
            4. 停止使用 temporary ClassLoader 进行类型匹配
            beanFactory.setTempClassLoader(null);
            5. 冻结全部bean定义，给被注册的bean定义发信号告诉它们今后不再被修改和进一步后续处理。它允许factory去积极缓存bean定义元数据。
            beanFactory.freezeConfiguration();
            6. 确保所有的非懒加载Bean都实例化，包括 FactoryBean
            beanFactory.preInstantiateSingletons();
          12. finishRefresh(); 完成BeanFactory的初始化创建工作；IOC容器就创建完成；发布相应事件；
          	1. clearResourceCaches(); 清除context资源缓存
          	2. initLifecycleProcessor(); 初始化 context 生命周期的后置处理器
          	3. getLifecycleProcessor().onRefresh(); 刷新传播到生命周期的处理器
          	4. publishEvent(new ContextRefreshedEvent(this)); 发布最终事件
          	5. LiveBeansView.registerApplicationContext(this);
          		如果过启用 spring.liveBeansView.mbeanDomain 将应用的上下文环境添加到 MBeanServer
          		
          13. resetCommonCaches(); 重置SpringCore的内省缓存，因为我们不在需要bean的元数据
          	ReflectionUtils.clearCache();
            AnnotationUtils.clearCache();
            ResolvableType.clearCache();
            CachedIntrospectionResults.clearClassLoader(getClassLoader());
            
  ============ 以上就是使用XML创建Spring运行环境并加载Bean的过程 ========
          	
            	
```

## Spring IOC - Annotation

```textmate

```



---

# Spring AOP

---

# Spring TX


