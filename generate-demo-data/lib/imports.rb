require 'rubygems'
require 'babel'
require 'ipsum'

module Babel
  
  # Babel bug, probably due to ruby changes
  def self.random_short(length = 70)
    (TEXT_1[rand(TEXT_1.size)] + ' ' + TEXT_2[rand(TEXT_2.size)] + ' ' + 
     TEXT_3[rand(TEXT_3.size)] + ' ' + TEXT_4[rand(TEXT_4.size)]).squeeze(' ')[0,length]
  end

end

class Ipsum
  
  # Ditto, strings changed a little
  def self.letter_following( sequence, position, language = self.default_language )
    dict = dictionary( language )
    sequence_statistics = dictionary( language )[ sequence ][ position ]
    if sequence_statistics
      letters = '' 
      sequence_statistics.each_pair do |character,amount|
        letters << character*amount
      end
      letters[rand(letters.size),1]
    end
  end
  
end