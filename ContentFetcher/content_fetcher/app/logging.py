import logging
import logstash

# Configure Logstash host and port
logstash_host = 'logstash'
logstash_port = 5000

logger = logging.getLogger('content-fetcher-logger')
logger.setLevel(logging.INFO)

# Add TCP handler
logger.addHandler(logstash.TCPLogstashHandler(
    host=logstash_host,
    port=logstash_port,
    version=1  # Logstash event schema version
))