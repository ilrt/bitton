require 'lib/imports'

class PublicationGenerator
  
  TYPES = [ 'bibo:JournalArticle' ]
  
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
    title = Babel.random_short + ' ' + Babel.random_short
    out.write(pub_id, 'rdfs:label', title)
    out.write(pub_id, 'dc:title', title)
    out.write(pub_id, 'dc:abstract', Babel.random_long)
    out.write(pub_id, 'dc:date', (1995 + rand(16)).to_s , 'xsd:gYear')
    out.write(pub_id, 'dc:type', TYPES[rand(TYPES.size)])
    out.write(pub_id, 'dc:partOf', greater_id)
    out.write(greater_id, 'rdfs:label', Babel.random_short)
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