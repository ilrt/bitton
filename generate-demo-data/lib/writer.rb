class Writer
  
  def initialize(out, prefixes = {})
    @out = out
    @prefixes = prefixes
    @record = -1
    start_record
  end
  
  def start_record
    @record += 1
    @graph = 'http://example.com/graph/' + @record.to_s
  end
  
  def write(s, p, o, dt = nil)
    sn = term(s)
    pn = term(p)
    on = term(o, dt)
    
    @out.puts "#{sn} #{pn} #{on} <#{@graph}>"
  end
  
  def term(s, dt = nil)
    expanded = expand(s)
    if expanded
      '<' + expanded + '>'
    else
      if dt
        '"' + escape(s) + '"^^<' + expand(dt) + '>'
      else
        '"' + escape(s) + '"'
      end
    end
  end
  
  def expand(s)
    matched = s.match(/^\w+:/)
    if matched
      if @prefixes.has_key?(matched[0])
        s.sub(matched[0], @prefixes[matched[0]])
      else
        s
      end
    else
      nil
    end
  end
  
  def escape(s)
    s = s.gsub("\\", "\\\\")
    s = s.gsub("\n", "\\n")
    s = s.gsub("\r", "\\r")
    s = s.gsub("\t", "\\t")
    s = s.gsub("\"", "\\\"")
  end
  
end