package org.ilrt.wf.facets.grails.controllers

import org.ilrt.wf.facets.grails.SPARQLQuery

class PublicationController {

    def publications = {
        [ results : SPARQLQuery.getResultSet(params: params) ]
    }
}
