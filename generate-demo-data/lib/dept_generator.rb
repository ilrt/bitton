require 'lib/imports'

class DeptGenerator
  
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
    uni_id = "http://example.com/organisation_units/univ#org"
    out.write(uni_id, "rdf:type", "aiiso:Institution")
    out.write(uni_id, "rdfs:label", "Example University")
    (@number / 10 + 1).times do |i|
      faculty_id = "http://example.com/organisation_units/faculty#{i}#org"
      out.write(faculty_id, "rdf:type", "aiiso:Institution")
      out.write(faculty_id, "rdfs:label", "Faculty #{i}")
      out.write(faculty_id, "aiiso:part_of", uni_id)
      out.write(faculty_id, "closed:part_of", uni_id)
      10.times do |j|
        num = i * 10 + j
        dept_id = "http://example.com/organisation_units/dept#{num}#org"
        out.write(dept_id, "rdf:type", "aiiso:Institution")
        out.write(dept_id, "rdfs:label", "Department #{num}")
        out.write(dept_id, "aiiso:part_of", faculty_id)
        out.write(dept_id, "closed:part_of", faculty_id)
        out.write(dept_id, "closed:part_of", uni_id)
      end
    end    
  end
  
  def get_random_dept
    num = rand(@number)
    fac_num = num / 10
    ["http://example.com/organisation_units/#{num}#org", 
    "http://example.com/organisation_units/faculty#{fac_num}#org",
    "http://example.com/organisation_units/univ#org"]
  end
  
  def each
    @number.times do |num|
      yield "http://example.com/organisation_units/#{num}#org"
    end
  end
  
end