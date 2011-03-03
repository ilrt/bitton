require 'lib/imports'

class PublicationGenerator
  
  TYPES = [ 'Journal Article', 'Conference Contribution', 'Book', 'Chapter in Book' ]
  
  def initialize(number)
    @number = number
  end
  
  def serialise(out)
    @number.times do |num|
      serialise_publication(num, out)
    end
  end
  
  def serialise_publication(num, out)
    out.start_record
    pub_id = "http://example.com/publications/#{num}#pub"
    greater_id = "http://example.com/journals/#{num}#pub"
    out.write(pub_id, "rdf:type", "dc:Publication")
    title = 1.sentences #Babel.random_short + ' ' + Babel.random_short
    out.write(pub_id, 'rdfs:label', title)
    out.write(pub_id, 'dc:title', title)
    out.write(pub_id, 'dc:abstract', (rand(7) * 2 + 4).sentences) #Babel.random_long)
    out.write(pub_id, 'dc:date', (1995 + rand(16)).to_s , 'xsd:gYear')
    type_name = TYPES[rand(TYPES.size)]
    type_id = 'http://example.com/types/' + type_name.gsub(/\s+/,'_')
    out.write(type_id, 'rdfs:label', type_name)
    out.write(pub_id, 'dc:type', type_id)
    out.write(pub_id, 'dc:partOf', greater_id)
    out.write(greater_id, 'rdfs:label', 1.sentences)
  end
  
  def get_random_publication
    "http://example.com/publications/#{rand(@number)}#pub"
  end
  
  def each
    @number.times do |num|
      yield "http://example.com/publications/#{num}#pub"
    end
  end
  
end