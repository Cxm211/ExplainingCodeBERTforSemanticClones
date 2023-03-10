public class Clone732AllCodeParts { 
public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException { 
     HttpServletRequest req = (HttpServletRequest) request; 
     if (req.getHeader ("x-dawson-nonce") == null || req.getHeader ("x-dawson-signature") == null) { 
         HttpServletResponse httpResponse = (HttpServletResponse) response; 
         httpResponse.setContentType ("application/json"); 
         httpResponse.sendError (HttpServletResponse.SC_BAD_REQUEST, "Required headers not specified in the request"); 
         return; 
     } 
     chain.doFilter (request, response); 
 } 
 
public void doFilter (ServletRequest arg0, ServletResponse response, FilterChain chain) throws IOException, ServletException {
logger.info("checking client id in filter");
HttpServletRequest request=(HttpServletRequest)arg0;
String clientId=request.getHeader("clientId");
if(StringUtils.isNotEmpty(clientId))
chain.doFilter(request,response);
logger.error("client id missing.");
}

}