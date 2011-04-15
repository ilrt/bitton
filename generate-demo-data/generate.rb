require 'rubygems'
require 'lib/writer'
require 'lib/people_generator'
require 'lib/publication_generator'
require 'lib/grant_generator'
require 'lib/dept_generator'

PREFIXES = {
'rdf:' => 'http://www.w3.org/1999/02/22-rdf-syntax-ns#' ,
'aiiso:' => 'http://purl.org/vocab/aiiso/schema#' ,
'foaf:' => 'http://xmlns.com/foaf/0.1/' ,
'proj:' => 'http://vocab.ouls.ox.ac.uk/projectfunding#' ,
'dc:' => 'http://purl.org/dc/terms/' ,
'rdfs:' => 'http://www.w3.org/2000/01/rdf-schema#' ,
'owl:' => 'http://www.w3.org/2002/07/owl#' ,
'closed:' => 'http://vocab.bris.ac.uk/rr/closed#' ,
'rr:' => 'http://vocab.bris.ac.uk/resrev#' ,
'rel:' => 'http://purl.org/vocab/relationship/' ,
'xsd:' => 'http://www.w3.org/2001/XMLSchema#'
}

out = Writer.new(STDOUT, PREFIXES)

people = PeopleGenerator.new 40
publications = PublicationGenerator.new 200
grants = GrantGenerator.new 30
depts = DeptGenerator.new 15

out.start_record
out.write(people.get_random_person, "foaf:nick", "bob")

people.serialise out
publications.serialise out
grants.serialise out
depts.serialise out

out.start_record
grants.each do |grant|
  (rand(2) + 1).times do
    (dept, fac, uni) = depts.get_random_dept
    out.write(grant, 'proj:hostedBy', dept)
    out.write(grant, 'closed:hostedBy', dept)
    out.write(grant, 'closed:hostedBy', fac)
    out.write(grant, 'closed:hostedBy', uni)
  end
  person = people.get_random_person
  out.write(grant, 'proj:hasPrincipalInvestigator', person)
  out.write(person, 'proj:contributesTo', grant)
  (rand(2) + 1).times do
    person = people.get_random_person
    out.write(grant, 'proj:hasCoInvestigator', person)
    out.write(person, 'proj:contributesTo', grant)
  end
end

out.start_record
people.each do |person|
  (dept, fac, uni) = depts.get_random_dept
  out.write(dept, 'foaf:member', person)
  out.write(dept, 'closed:member', person)
  out.write(fac, 'closed:member', person)
  out.write(uni, 'closed:member', person)
end

out.start_record
publications.each do |pub|
  (rand(2) + 1).times do
    (dept, fac, uni) = depts.get_random_dept
    out.write(pub, 'rr:department', dept)
    out.write(pub, 'closed:department', dept)
    out.write(pub, 'closed:department', fac)
    out.write(pub, 'closed:department', uni)
  end
  person = people.get_random_person
  (rand(3) + 1).times do
    person = people.get_random_person
    out.write(pub, 'dc:contributor', person)
  end
end