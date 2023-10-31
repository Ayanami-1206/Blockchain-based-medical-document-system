from mininet.topo import Topo

class MyTopo( Topo ):
    "Simple topology example."

    def build( self ):
        "Create custom topo."

        # Add switches
        				
        Switch1 = self.addSwitch( 's1' )
    
 
         # Add Hosts 
        Host1 = self.addHost( 'h1' )
        Host2 = self.addHost( 'h2' )
        Host3 = self.addHost( 'h3' )
       	Host4 = self.addHost( 'h4' )
    	Host5 = self.addHost( 'h5' )
    	Host6 = self.addHost( 'h6' )
        Host7 = self.addHost( 'h7' )
        Host8 = self.addHost( 'h8' )
       	Host9 = self.addHost( 'h9' )
    	Host10 = self.addHost( 'h10' )
    	
    	
  
        # Add links between Hosts
        self.addLink( Switch1, Host1 )
        self.addLink( Switch1, Host2 )
        self.addLink( Switch1, Host3 )
        self.addLink( Switch1, Host4 )
        self.addLink( Switch1, Host5 )
        self.addLink( Switch1, Host6 )
        self.addLink( Switch1, Host7 )
        self.addLink( Switch1, Host8 )
        self.addLink( Switch1, Host9 )
        self.addLink( Switch1, Host10 )
      
      
       
        
       


topos = { 'mytopo': ( lambda: MyTopo() ) }


