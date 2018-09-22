package tr1nks.service.domain;

import tr1nks.domain.entity.DomainsEntity;

public interface DomainsService {
    String getEmailDomen();

    String getImagineDomen();

    String getOfficeDomen();

    DomainsEntity save(DomainsEntity domainsEntity);
}
