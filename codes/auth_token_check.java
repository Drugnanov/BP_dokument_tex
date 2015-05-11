@Component
public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

    ...

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException, PermissionDeniedException {
        String token = ((HttpServletRequest) request).getHeader("token");
        if (token != null && !token.isEmpty()) {
            // validate the token
            try {
                if (tokenUtils.validate(token)) {
                    // determine the user based on the (already validated) token
                    Person person = tokenUtils.getUserFromToken(token);
                    // build an Authentication object with the user's info
                    UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken
                        (person.getUsername(),
                            person.getPassword());
                    authentication.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(
                                (HttpServletRequest) request));
                    // set the authentication into the SecurityContext
                    SecurityContextHolder.getContext()
                        .setAuthentication(
                            authManager
                                .authenticate(authentication));
                }
            } catch (AuthenticationException e) {
                SecurityContextHolder.clearContext();
                customAuthEP.commence((HttpServletRequest) request, (HttpServletResponse) response, e);
            } catch (Throwable e) {
                SecurityContextHolder.clearContext();
                customAuthEP.commence((HttpServletRequest) request, (HttpServletResponse) response,
                        new BadCredentialsException
                            ("BadCredentialsException:" + e.getMessage()
                                , e));
            }
        }
        // continue thru the filter chain
        chain.doFilter(request, response);
    }
}