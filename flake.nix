{
  description = "ZFGC Development Environment";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs?ref=nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = import nixpkgs { inherit system; };
      in
      {
        devShells.default = pkgs.mkShell {
          name = "spring-boot-dev";

          packages = with pkgs; [
            openjdk21            
            maven                
            docker               
            docker-compose       
            git
            jq
            curl
            unzip
            postgresql          
            pgcli               
          ];

          JAVA_HOME = pkgs.openjdk21.home;
        };
      });
}
