package com.elice.bookstore.config.audit;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*
  JpaAuditingConfiguration :
  작성 : 이하영
  : @WebMvcTest을 사용한 슬라이스 테스트를 진행 중에 아래와 같은 Exception이 발생해 해결 방법을 찾아 작성하게 되었습니다.
  BeanCreationException
  - Error creating bean with name 'jpaAuditingHandler': Cannot resolve reference to bean 'jpaMappingContext' while setting constructor argument
  - Error creating bean with name 'jpaMappingContext': JPA metamodel must not be empty
  IllegalArgumentException
  - JPA metamodel must not be empty

  원인 (정확하지 않을 수 있으나)
  - Test를 수행할 때는 DB를 사용하지 않는다.
  - 슬라이스 테스트 수행 시 별도로 import를 하지 않는 이상 Jpa와 연관된 Bean을 로드하지 않기 때문에
    @EnableJpaAuditiong에서 요구하는 Jpa 관련 Bean을 등록할 수 없는 현상이 발생한다.

  제가 찾아본 해결방법 2가지 입니다.
  - @Configuration으로 테스트와 분리합니다. (지금 이 클래스처럼)
    그렇기 때문에, BookStoreApplication 클래스에는 @EnableJpaAuditing을 삭제했습니다.
  - 테스트 클래스 마다 @MockBean(JpaMetamodelMappingContext.class) 붙여주기
    이 방법은, 모든 테스트에 위 어노테이션을 붙여줘야하고 사용하지 않는 기능에 대한 추가작업을 진행해야 해서 번거로울 수 있다고 합니다!
*/
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration { }
