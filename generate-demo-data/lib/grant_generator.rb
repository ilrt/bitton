require 'lib/imports'

class GrantGenerator
  
  FUNDERS = [ 'NERC', 'BBSRC', 'EPSRC', 'JISC' ]
  
  def initialize(number)
    @number = number
  end
  
  def serialise(out)
    @number.times do |num|
      serialise_grant(num, out)
    end
  end
  
  def serialise_grant(num, out)
    out.start_record
    grant_id = "http://example.com/grants/#{num}#grant"
    out.write(grant_id, "rdf:type", "proj:Grant")
    title = 1.sentences #Babel.random_short + ' ' + Babel.random_short
    out.write(grant_id, 'rdfs:label', title)
    out.write(grant_id, 'dc:title', title)
    out.write(grant_id, 'dc:abstract', (rand(7) * 2 + 4).sentences) #Babel.random_long)
    start = 1995 + rand(16)
    endd = start + rand(9)
    out.write(grant_id, 'proj:startDate', random_date(start) , 'xsd:date')
    out.write(grant_id, 'proj:endDate', random_date(endd) , 'xsd:date')
    out.write(grant_id, 'proj:value', rand(2000000).to_s , 'xsd:int')
    funder = FUNDERS[rand(FUNDERS.size)]
    funder_id = 'http://example.com/funder/' + funder
    out.write(funder_id, 'proj:funds', grant_id)
    out.write(funder_id, 'rdfs:label', funder)
  end
  
  def get_random_grant
    "http://example.com/grants/#{rand(@number)}#grant"
  end
  
  def each
    @number.times do |num|
      yield "http://example.com/grants/#{num}#grant"
    end
  end
  
  def random_date(year)
    "%04d-%02d-%02d" % [year, rand(12) + 1, rand(28) + 1]
  end
  
end