# java-webMVC

> KUIT 4기 서버 4~7주차 강의와 미션 수행을 위한 레포지토리입니다.

- `step1-servlet-completed`
    - 모든 서블릿(컨트롤러)이 요청을 직접 받고 직접 리다이렉트/포워딩을 수행
- `step2-mvc-completed`
    - DispatcherServlet이 모든 요청을 받음 → RequestMapping → Controller에게 요청 처리를 위임
    - Controller들은 모두 viewName을 반환한다.
    - viewName을 받은 DispatcherServlet이 리다이렉트/포워딩을 수행
- `step3-jdbc-start`
    - step2와 동일한 구조로 요청을 처리
    - json을 반환하는 컨트롤러가 생김
- `step4-mvc-view`
    - View 인터페이스를 만들고 각 컨트롤러는 View 구현체를 반환 (JspView, JsonView 등)
- `step5-answer-modelAndView`
    - 컨트롤러는 ModelAndView를 반환
    - ModelAndView는 model이라는 Map 자료구조와 View를 갖는다

      model을 분리함으로써 사용자에게 보여줄 정보를 선택할 수 있음
- `step6-mvc-viewResolver`
    - model과 view를 분리
    - 컨트롤러를 실행시킬 때 파라미터로 Map<String, String> params와 Map<String, Object> model을 넘긴다.
    - 컨트롤러는 String viewName을 반환한다.
    - DispatcherServlet이 viewResolver를 호출하여 viewName을 View 객체로 반환한다.
- `step7-spring-mvc`
    - ControllerV1 : ModelAndView를 반환하는 컨트롤러
    - ControllerV2 : 뷰 이름(String viewName)을 반환하는 컨트롤러

  이것들을 모두 함께 사용하기 위해 HandlerAdapter를 도입
