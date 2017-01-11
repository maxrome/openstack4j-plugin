package org.openbaton.drivers.openstack4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openbaton.catalogue.mano.common.DeploymentFlavour;
import org.openbaton.catalogue.nfvo.NFVImage;
import org.openstack4j.model.compute.Address;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.image.Image;

/** Created by lto on 10/01/2017. */
public class Utils {
  public static DeploymentFlavour getFlavor(Flavor flavor) {
    DeploymentFlavour flavour = new DeploymentFlavour();
    DeploymentFlavour deploymentFlavour = new DeploymentFlavour();
    deploymentFlavour.setFlavour_key(flavor.getName());
    deploymentFlavour.setExtId(flavor.getId());
    deploymentFlavour.setDisk(flavor.getDisk());
    deploymentFlavour.setRam(flavor.getRam());
    deploymentFlavour.setVcpus(flavor.getVcpus());
    return flavour;
  }

  public static org.openbaton.catalogue.nfvo.Server getServer(Server srv) {
    org.openbaton.catalogue.nfvo.Server server = new org.openbaton.catalogue.nfvo.Server();
    server.setName(srv.getName());
    server.setExtId(srv.getId());
    server.setCreated(srv.getCreated());
    server.setExtendedStatus(srv.getStatus().value());
    server.setHostName(srv.getName()); // TODO which one is correct?
    server.setInstanceName(srv.getInstanceName());
    HashMap<String, List<String>> ips = new HashMap<>();
    for (Map.Entry<String, List<? extends Address>> address :
        srv.getAddresses().getAddresses().entrySet()) {
      List<String> adrs = new ArrayList<>();
      for (Address ip : address.getValue()) {
        adrs.add(ip.getAddr());
      }
      ips.put(address.getKey(), adrs);
    }
    server.setIps(ips);
    server.setFlavor(Utils.getFlavor(srv.getFlavor()));
    server.setHypervisorHostName(srv.getHypervisorHostname());
    //TODO list floating ips
    //server.setFloatingIps();
    return server;
  }

  public static NFVImage getImage(Image image) {
    NFVImage nfvImage = new NFVImage();
    nfvImage.setName(image.getName());
    nfvImage.setExtId(image.getId());
    nfvImage.setMinRam(image.getMinRam());
    nfvImage.setMinDiskSpace(image.getMinDisk());
    nfvImage.setCreated(image.getCreatedAt());
    nfvImage.setUpdated(image.getUpdatedAt());
    nfvImage.setIsPublic(image.isPublic());
    nfvImage.setDiskFormat(image.getDiskFormat().toString().toUpperCase());
    nfvImage.setContainerFormat(image.getContainerFormat().toString().toUpperCase());
    return nfvImage;
  }
}