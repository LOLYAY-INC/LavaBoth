package io.lolyay.jlavalink.data;

public record JLavaLinkClientInfo(String version,
                                  long botUserId,
                                  String clientName,
                                  ConnectionInfo connection,
                                  boolean verbose,
                                  int defaultVolume
                                  ){}
