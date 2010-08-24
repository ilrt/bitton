/*
 *  © University of Bristol
 */

package org.ilrt.wf.facets.cas;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Chris Bailey (c.bailey@bristol.ac.uk)
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    final static GrantedAuthority auth = new GrantedAuthorityImpl("ROLE_USER");

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException, DataAccessException {

        List<GrantedAuthority> l = new ArrayList<GrantedAuthority>();
        l.add(auth);

        return new User(s, "null", true, true, true, true, l);
    }
}

