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

Arbitrary choices:
* Document responses (representing single entity) are without envelopes.
* Simple collection responses (no pagination/filtering/sorting) are without envelopes.
* Complex collection responses (pagination/filtering/sorting) are wrapped in a unified envelope.
There are alternatives like using headers, but community is far from unified on this. Also `Range`
headers are not usable with `POST` requests to `search` controllers (alternative to `GET` for more
complex filtering). HATEOAS on pagination (like next/prev links) is possible, but omitted here.
* Error responses are unified.

TODO:
* Separate model for API instead of reuse of entities.
* Demonstrate field inclusion/exclusion.
* Security for both application and Swagger definition.
* Tests using Spring? (A bit beyond the scope as the whole demo is just supporting act for
YAML-based Swagger config.)