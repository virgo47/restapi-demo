# REST API demo "Boxes and Items"

This demo is proof of concept (or "reference implementation") of a set of REST API rules
(not published here). It is not fully REST compliant (e.g. HATEOAS is missing), instead it
reflects so called "pragmatic REST" approach.

Goals:
* Implement REST-like API demo in some mainstream technology (Spring Boot).
* Provide Swagger documentation to it.
* Demonstrate the most common rules in practice.

It does not have a real data layer, repositories use maps to store the data which are
pre-populated after ever restart (data changes are possible but eventually ephemeral).

TODO:
* Separate model for API instead of reuse of entities.
* Demonstrate field inclusion/exclusion.