require 'rubygems'
require 'babel'

class PeopleGenerator
  
  def initialize(number)
    @number = number
  end
  
  def serialise(out)
    @number.times do |num|
      serialise_person(num, out)
    end
  end
  
  def serialise_person(num, out)
    out.start_record
    person_id = "http://example.com/people/#{num}#person"
    out.write(person_id, "rdf:type", "foaf:Person")
    name = ( rand(2) == 1 ? Babel.random_male_name : Babel.random_female_name ) + 
      ' ' + Babel.random_surname
    out.write(person_id, 'rdfs:label', name)
    out.write(person_id, 'foaf:name', name)
    out.write(person_id, 'foaf:mbox', 'mailto:' + name.sub(' ', '.') + '@example.com' )
  end
  
  def get_random_person
    "http://example.com/people/#{rand(@number)}#person"
  end
  
  def each
    @number.times do |num|
      yield "http://example.com/people/#{num}#person"
    end
  end
  
end