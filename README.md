# TODO
Create README file
Document classes and pom.xml
Logging
Add tests
 * integration tests
 * handle 127.0.0.1:8080/ - error.html
 * -- https://wiki.eclipse.org/Jetty/Howto/Custom_Error_Pages#Define_error_pages_in_web.xml
 * logging
 * Test
 * POST
 * - invalid object: No value for a name, names other than body, embedded "
 * - valid input object, creates note with unique UUID, valid response
 *
 * GET
 * - No arguments return empty json if no notes
 * - Gets all notes if there are more than 0
 * - Get id: UUID - returns note if found, else error
 * - test with extra path api/notes/{uuid}/foo/bar
 * - test multi-line body
 * - test max length body
 * - test empty and blank body
 * Scaling, multi-user, injection for testability


